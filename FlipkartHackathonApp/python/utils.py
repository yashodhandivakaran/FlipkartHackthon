from __future__ import print_function
import pickle
import os

def store_classifier(classifier,file_name):
	""" Serialize the classifier """
	f = open(file_name,"wb")
	pickle.dump(classifier,f)
	f.close()

def load_classifier(file_name):
	""" Deserialize the classifier """
	f = open(file_name,"rb")
	classifier = pickle.load(f)
	f.close()
	return classifier

