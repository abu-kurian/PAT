import cv2
import numpy as np
import sys

cascPath=sys.argv[1]
imagePath=sys.argv[2]

cascade=cv2.CascadeClassifier(cascPath)

image=cv2.imread(imagePath)
gray=cv2.cvtColor(image,cv2.COLOR_BGR2GRAY)

faces=cascade.detectMultiScale(gray,scaleFactor=1.1,minNeighbors=5,minSize=(30,30),flags=cv2.CASCADE_SCALE_IMAGE)


print'found 0 faces'.format(len(faces))

count=0

for(x,y,w,h) in faces:
	cv2.rectangle(image,(x,y),(x+w,y+h),(0,255,0),2)
	count=count + 1

cv2.imshow('number faces:',image)
print'%d'%(count)

cv2.waitkey(0)
