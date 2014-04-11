import flickr
import pymongo
from pymongo import MongoClient
import time
from datetime import datetime

# set up the mongoDB environment, including the database, collections
client = MongoClient()
db = client.flickrDB
collection = db.image
#print collection.find_one()

var=1
#collecting data using flickr API, can set search parameters below:
# tags, upload_time, has_geo, location, numbers_per_page, etc
#the time interval is set as 30 minutes
while var==1:
	#photos=flickr.photos_search(per_page='250',has_geo='true')
	#get the latest image API
	photos=flickr.photos_get_recent(per_page='500',extras='geo')
	for photo in photos:
		try:
			pid= photo.id
			title= photo.title
			upload_time= photo.dateposted
			url= photo.getMedium()
			location= photo.getLocation()
			likes= photo.getFavoriteCount()
			tags= []
			if hasattr(photo,'tags'):
				if None!=photo.tags:
					for t in photo.tags:
						tags.append(t.text)
			docu={'photo_id':pid,'Title':title,'UploadTime':upload_time,'URL':url,'Location':location,'Likes':likes,'Tag':tags}
			#if None==collection.find({'_id':pid}):
			collection.insert(docu)
			print pid
		except flickr.FlickrError:
			print "Flickr API error"
		except IOError:
			print "connection error"
		#print pid
		#print title
		#print upload_time
		#print url
		#print location
		#print likes
		#print tags
		#time.sleep(2)
	
	time.sleep(300)
	print datetime.now().strftime("%Y-%m-%d %H:%M:%S")
