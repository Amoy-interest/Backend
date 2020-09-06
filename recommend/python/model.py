'''
1.从blog_vote中获取点赞信息,使用svd填补缺失值
'''
# 导入模块
import pymysql
import datetime
import time
import random
import numpy as np
import pandas as pd
import threading
from surprise import Reader, Dataset, SVD
from collections import defaultdict
import math
from sklearn.linear_model import LogisticRegression

class DB:
    
    conn = pymysql.connect(
             host="mycat",
	     	 port=8066,
             user="root",
             password="amoy123",
             database="amoy",
             charset="utf8")

    def connect(self):
        self.conn = pymysql.connect(
             host="mycat",
	     	 port=8066,
             user="root",
             password="amoy123",
             database="amoy",
             charset="utf8")

    def Exe(self, sql):
        try:
            cursor = self.conn.cursor()
            cursor.execute(sql)
            self.conn.commit()
        except pymysql.OperationalError:
            self.connect()
            cursor = self.conn.cursor()
            cursor.execute(sql)
            self.conn.commit()
        return cursor

db = DB()


def getDataFromBlogVote(user_len, blog_len):

	#sql = "select user_id,blog_id from blog_vote where user_id < 3 and create_time >= date_sub(curdate(), interval 7 day)"
	sql = "select user_id,blog_id from blog_vote where create_time >= date_sub(curdate(), interval 7 day)"

	cursor1 = db.Exe(sql)

	result0 = list(cursor1.fetchall())

	user_vote = [list() for i in range(user_len + 1)]

	for item in result0:
		user_vote[item[0]].append(item[1])

	return user_vote

def recall(user_group, user_len, blog_len):
	result1 = getDataFromBlogVote(user_len, blog_len)

	top_n = [ [] * 1000 for row in range(user_len + 1)]

	for i in range(1, 32):
		user_list = []
		user_list.extend(user_group[i])
		tlist = []

		for user_id in user_list:
			tlist.extend(result1[user_id])

		for user_id in user_list:
			top_n[user_id].extend(random.sample(tlist, 500))

	print("finish recall")

	return top_n

def build_blog_vector(user_len, blog_len):

	#sql = "select * from topic_blog where blog_id < 3"

	sql = "select * from topic_blog"

	cursor1 = db.Exe(sql)

	result = cursor1.fetchall()

	blog_vectors = [ [] * 5 for row in range(blog_len+1)]

	for row in result:

		if (row[1] <= blog_len):

			blog_vectors[row[1]].append(row[0])

	print("finish build blog vectors")

	return blog_vectors

def build_user_vector(blog_vectors, user_len, blog_len):

	sql = "select blog_id,user_id,create_time from blog_vote where create_time >= date_sub(curdate(), interval 7 day)"
	#sql = "select blog_id,user_id,create_time from blog_vote where user_id < 3 and create_time >= date_sub(curdate(), interval 7 day)"
	cursor1 = db.Exe(sql)

	result = cursor1.fetchall()

	cur_ts = time.mktime((datetime.datetime.now().timetuple()))

	user_vectors = []


	#for i in range(0, 3):
	for i in range(0, user_len + 1):

		user_vectors.append([0] * 310)

	for row in result:

		vote_ts = time.mktime(row[2].timetuple())

		factor = math.exp(-0.012 * (cur_ts - vote_ts) / 3600)

		# 怎么返回一个数组啊

		for k in range(0, 5):

			user_vectors[row[1]][(int)((blog_vectors[row[0]][k] - 1)/ 10)] += factor

	'''
	for i in range(0, 5):

		print(user_vectors[1][i])
	'''
	print("finish build user_vectors")

	return user_vectors

def get_user_recommend(user_len, blog_len):

	#sql = "select user_id, recommend_blogs from recommend_history where user_id < 3 and create_time >= date_sub(curdate(), interval 6 day)"

	#取前一天的推荐数据

	sql = "select user_id, blog_id from recommend_blogs"

	cursor1 = db.Exe(sql)

	result1 = cursor1.fetchall()

	result_list = [list() for i in range(user_len + 1)]

	for row in result1:

		result_list[row[0]].append(row[1])

	return result_list

def predictCTR(reData, blog_vectors, user_group, user_len, blog_len):

	user_vectors = build_user_vector(blog_vectors, user_len, blog_len)

	#获取训练集点赞数据
	user_vote = getDataFromBlogVote(user_len, blog_len)

	# 获取训练集推荐数据
	user_recommend = get_user_recommend(user_len, blog_len)

	user_recommend_2 = []

	user_recommend_2.append([])

	for user_id in range(1, user_len + 1):

		user_recommend_2.append([i for i in user_recommend[user_id] if i not in user_vote[user_id]])

	# 构建矩阵

	weight = [[0] * 1000 for i in range(0, user_len + 1)]

	for i in range(1, 32):
		user_list = []
		user_list.extend(user_group[i])

		data_x = []

		data_y = []

		for user_id in user_list:

			if (len(user_vote[user_id]) > 20):

				vote_blogs = random.sample(user_vote[user_id], 20)

			else:

				vote_blogs = user_vote[user_id]

			for blog_id in vote_blogs:

				row_part1 = []
				row_part1.extend(user_vectors[user_id])

				row_part2 = [0] * 310

				for j in range(0, 5):

					row_part2[(int)((blog_vectors[blog_id][j] - 1)/ 10)] += 0.1

				row_part1.extend(row_part2)

				data_x.append(row_part1)
				data_y.append([1])

		for user_id in user_list:

			if (len(user_recommend_2[user_id]) > 40):

				rec_blogs = random.sample(user_recommend_2[user_id], 40)

			else:
				rec_blogs = user_recommend_2[user_id]

			for blog_id in rec_blogs:

				row_part1 = []
				row_part1.extend(user_vectors[user_id])

				row_part2 = [0] * 310

				for j in range(0, 5):

					row_part2[(int)((blog_vectors[blog_id][j] - 1)/ 10)] += 0.1

				row_part1.extend(row_part2)

				data_x.append(row_part1)
				data_y.append([0])

		print("finish build matrix")

		# 拟合

		lr = LogisticRegression()
		lr.fit(data_x, data_y)

		print("finish lr fit")


		# 预测 排序

		#for i in range(1, 3):
		for user_id in user_list:
			predict_x = []
			for j in range(0, 1000):

				blog_id = reData[i][j]

				row_part1 = []

				row_part1.extend(user_vectors[user_id])

				row_part2 = [0] * 310

				for j in range(0, 5):

					row_part2[(int)((blog_vectors[blog_id][j] - 1)/ 10)] += 0.1

				row_part1.extend(row_part2)

				predict_x.append(row_part1)

			predictions = lr.predict_proba(predict_x)

			for j in range(0, 1000):

				weight[user_id][j] += predictions[j][1]

	sql = "delete from recommend_blogs"

	db.Exe(sql)

	for user_id in range(1, user_len + 1):

		slist = (np.argsort(weight[user_id]))[800: 1000]

		rec_list = []

		if (user_id == 1):
			print("slist长度是否为200:",len(slist))

		for i in range(0, 200):

			rec_list.append(reData[user_id][slist[i]])

		dt = datetime.date.today()

		for rec_id in rec_list:

			sql = "insert into recommend_blogs values(%d, %d, 0)" %(user_id, rec_id)

			db.Exe(sql)

	print("finish recommend blog")

def update_user_group(blog_vectors, user_len, blog_len):

	sql = "select blog_id,user_id,create_time from blog_vote where create_time >= date_sub(curdate(), interval 7 day)"
	#sql = "select blog_id,user_id,create_time from blog_vote where user_id < 3 and create_time >= date_sub(curdate(), interval 7 day)"
	cursor1 = db.Exe(sql)

	result = cursor1.fetchall()

	cur_ts = time.mktime((datetime.datetime.now().timetuple()))

	user_vectors = []


	#for i in range(0, 3):
	for i in range(0, user_len + 1):

		user_vectors.append([0] * 32)

	for row in result:

		vote_ts = time.mktime(row[2].timetuple())

		factor = math.exp(-0.012 * (cur_ts - vote_ts) / 3600)

		# 怎么返回一个数组啊

		for k in range(0, 5):

			user_vectors[row[1]][(int)((blog_vectors[row[0]][k] - 1)/ 100) + 1] += factor

	user_group = [list() for i in range(32)]

	for user_id in range(1, user_len + 1):

		groups = (np.argsort(user_vectors[user_id]))[30: 32]

		user_group[groups[0]].append(user_id)

		user_group[groups[1]].append(user_id)
		'''

		if (user_id == 335):

			print(np.argsort(user_vectors[user_id]))
		'''

	return user_group

def cal_sim_user(user_len, blog_len):

	sql = "delete from sim_user"

	db.Exe(sql)

	follow_list = [list() for i in range(user_len + 1)]

	user_list = [list() for i in range(user_len + 1)]

	sql = "select * from user_follow"

	cursor1 = db.Exe(sql)

	user_follow = list(cursor1.fetchall())

	for item in user_follow:

		follow_list[item[0]].append(item[1])

		user_list[item[1]].append(item[0])

	for user_id in range(1, user_len + 1):

		weight = [0] * (user_len + 1)

		for fan_id in user_list[user_id]:

			for follow_id in follow_list[fan_id]:

				weight[follow_id] += 1


		cand_list = []

		for i in range(1, user_len + 1):

			if (weight[i] != 0):

				cand_list.append([i, weight[i] / (len(user_list[user_id]) + len(user_list[i]) - weight[i])])

		cand_list = sorted(cand_list, key=(lambda x:x[1]), reverse = True)

		sim_user = cand_list[1: 8]

		sim_user = [x[0] for x in sim_user]

		sql = "insert into sim_user values(%d, %d),(%d, %d),(%d, %d),(%d, %d),(%d, %d),(%d, %d),(%d, %d)" % (user_id, sim_user[0], user_id, sim_user[1],user_id, sim_user[2],user_id, sim_user[3],user_id, sim_user[4],user_id, sim_user[5],user_id, sim_user[6])

		db.Exe(sql)

def cal_sim_blog(user_len, blog_len):

	sql = "delete from sim_blog"

	db.Exe(sql)

	sql = "select blog_id,user_id from blog_vote where create_time >= date_sub(curdate(), interval 7 day)"

	cursor1 = db.Exe(sql)

	vote_list = list(cursor1.fetchall())

	blog_list = [list() for i in range(user_len + 1)]

	user_list = [list() for i in range(blog_len + 1)]

	for item in vote_list:

		blog_list[item[1]].append(item[0])

		user_list[item[0]].append(item[1])

	for blog_id in range(1, blog_len + 1):

		weight = [0] * (blog_len + 1)

		for user_id in user_list[blog_id]:

			for tmp in blog_list[user_id]:

				weight[tmp] += 1


		cand_list = []

		for i in range(1, blog_len + 1):

			if (weight[i] != 0):

				cand_list.append([i, weight[i] / (len(user_list[blog_id]) + len(user_list[i]) - weight[i])])

		cand_list = sorted(cand_list, key=(lambda x:x[1]), reverse = True)

		sim_blog = cand_list[1: 8]

		sim_blog = [x[0] for x in sim_blog]

		blogs_str = ','.join([str(x) for x in sim_blog])

		sql = "insert into sim_blog values(%d, %d),(%d, %d),(%d, %d),(%d, %d),(%d, %d),(%d, %d),(%d, %d)" % (blog_id, sim_blog[0], blog_id, sim_blog[1],blog_id, sim_blog[2],blog_id, sim_blog[3],blog_id, sim_blog[4],blog_id, sim_blog[5],blog_id, sim_blog[6])
		db.Exe(sql)

def recommend_sys():

	print("begin")

	sql1 = "SELECT count(*) FROM user"

	cursor1 = db.Exe(sql1)

	user_len = (list(cursor1.fetchone()))[0]

	sql2 = "SELECT count(*) FROM blog"

	cursor1 = db.Exe(sql2)

	blog_len = (list(cursor1.fetchone()))[0]

	print("blog_len", blog_len)

	print("user_len", user_len)

	blog_vectors = build_blog_vector(user_len, blog_len)

	user_group = update_user_group(blog_vectors, user_len, blog_len)

	top_n = recall(user_group, user_len, blog_len)

	predictCTR(top_n, blog_vectors, user_group, user_len, blog_len)

	cal_sim_user(user_len, blog_len)

	cal_sim_blog(user_len, blog_len)

	timer = threading.Timer(85200, recommend_sys)

	timer.start()

if __name__ == "__main__":

	now_time = datetime.datetime.now()

	next_time = now_time + datetime.timedelta(days=0)

	next_year = next_time.date().year

	next_month = next_time.date().month

	next_day = next_time.date().day

	next_time = datetime.datetime.strptime(str(next_year)+"-"+str(next_month)+"-"+str(next_day)+" 15:30:00", "%Y-%m-%d %H:%M:%S")

	print("start_time:", next_time)

	timer_start_time = (next_time - now_time).total_seconds()

	timer = threading.Timer(timer_start_time, recommend_sys)

	timer.start()
