# load all images into a list
import pandas as pd # data processing, CSV file I/O (e.g. pd.read_csv)
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import numpy as np
import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.preprocessing.image import img_to_array, load_img
import random
import os
from scipy import misc, ndimage

def resize(image):
    return misc.imresize(image, (384, 512))

model=tf.keras.models.load_model('/home/raghav/Downloads/waste_management_Datasets/Rubbish_classification/models/rps.h5')

images = []
img_folder = os.path.join('/home/raghav/Downloads/waste_management_Datasets/Rubbish_classification/data/validation_data')
img_files = os.listdir(img_folder)
img_files = [os.path.join(img_folder, f) for f in img_files]
# print(img_files)
for img in img_files:
    print(img)
    img = load_img(img, target_size=(150, 150))
    img = img_to_array(img)
    img = np.expand_dims(img, axis=0)
    images.append(img)

# stack up images list to pass for prediction
images = np.vstack(images)
# print(images)
classes = model.predict_classes(images, batch_size=10)
print(classes)