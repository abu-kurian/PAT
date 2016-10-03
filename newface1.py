import numpy as np
import cv2
 
fcascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
ecascade = cv2.CascadeClassifier('haarcascade_eye.xml')
 
img = cv2.imread('pic1.jpg')
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

faces = fcascade.detectMultiScale(gray, 1.3, 5)
for (x,y,w,h) in faces:
     cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)
     roi_gray = gray[:y+h, :x+w]
     roi_color = img[:y+h, :x+w]
     eyes = ecascade.detectMultiScale(roi_gray)
     for (ex,ey,ew,eh) in eyes:
         cv2.rectangle(roi_color,(ex,ey),(ex+ew,ey+eh),(0,255,0),2)
 
cv2.imshow('img',img)
cv2.waitKey(0)
cv2.destroyAllWindows()