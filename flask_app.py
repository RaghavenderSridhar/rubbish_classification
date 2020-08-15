import os
from PIL import Image
import tensorflow as tf
from tensorflow.keras.preprocessing.image import img_to_array, load_img
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image as img
from keras.preprocessing.image import img_to_array
import numpy as np
from PIL import Image
from datetime import datetime
import io
from flask import Flask,Blueprint,request,render_template,jsonify
from flask import render_template, request, jsonify
import flask
import numpy as np
import traceback
import re
import pandas as pd
 

# importing models
model=tf.keras.models.load_model('/home/raghav/Downloads/waste_management_Datasets/Rubbish_classification/models/rps.h5')
 

app = Flask(__name__)

# Testing URL

@app.route('/', methods = ['GET', 'POST'])

def predict_image():

    print(request.files)

    imgData = request.files['image']
   

    img=Image.open(imgData)

    img = img.resize((150,150))

    #print(imgData)

    # img = load_img(img, target_size=(150, 150))

    img = img_to_array(img)

    img = np.expand_dims(img, axis=0)

    classes = str(model.predict_classes(img, batch_size=10))

    return classes

if __name__ == '__main__':

    app.run(host="0.0.0.0", port=5000, debug=False)

