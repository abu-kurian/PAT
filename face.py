import cv2

def detect(path):
    img = cv2.imread(path)
    cascade = cv2.CascadeClassifier("/usr/share/opencv/haarcascades/haarcascade_frontalface_alt.xml")
    rects =  cascade.detectMultiScale(img, scaleFactor=1.3, minNeighbors=4, minSize=(30, 30), flags = cv2.CASCADE_SCALE_IMAGE)

    if len(rects) == 0:
        return [], img
    rects[:, 2:] += rects[:, :2]
    return rects, img

def box(rects, img):
    for x1, y1, x2, y2 in rects:
        cv2.rectangle(img, (x1, y1), (x2, y2), (127, 255, 0), 2)
    cv2.imwrite('/home/abu/opencv/opencv-3.0.0-alpha/samples/cpp/tutorial_code/images/lena.png', img);

rects, img = detect("/home/abu/opencv/opencv-3.0.0-alpha/samples/cpp/tutorial_code/images/lena.png")
box(rects, img)
vis=img.copy()
cv2.imshow('facedetect', vis)

