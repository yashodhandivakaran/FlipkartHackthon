from textblob.classifiers import NaiveBayesClassifier
import argparse
import utils
import data
import random
from nltk.stem.snowball import SnowballStemmer


CLASSIFIER_FILE_NAME = 'classifier.pickle'

def load_data(file_path):
	""" Read the file into textblob understandable format"""
	with open(file_path) as f:
		data = []
		for line in f:
			line = line.decode('utf-8').strip()
			if line:
				data.append(tuple(line.rsplit(',',1)))
	return data

def get_features(document):
	document_words = set(document.split())
	s = SnowballStemmer("english")
	stemmed_words = [ s.stem(word) for word in document_words ]
	features = {}
	features['count'] = len(document_words)
	for word in data.wordlist:
		features['contains({})'.format(s.stem(word))] = (word in stemmed_words)
	return features

def train(file_path):
	""" train the classifier using the provided file """
	data = load_data(file_path)
	random.shuffle(data)
	classifier = NaiveBayesClassifier(data,feature_extractor=get_features)
	#classifier = NaiveBayesClassifier(data)
	utils.store_classifier(classifier,CLASSIFIER_FILE_NAME)

def train_n_test(file_path):
	data = load_data(file_path)
	random.shuffle(data)
	train = data[0:110]
	test = data[110:]
	#classifier = NaiveBayesClassifier(train)
	classifier = NaiveBayesClassifier(train,feature_extractor=get_features)
	print classifier.accuracy(test)

def classify_text(text):
	 classifier = utils.load_classifier(CLASSIFIER_FILE_NAME)
	 prob_dist = classifier.prob_classify(text)
	 max_val =  prob_dist.max()
	 print max_val
	 print round(prob_dist.prob("general"), 2)
	 print round(prob_dist.prob("app"), 2)
	 print round(prob_dist.prob("pricing"), 2)
	 print classifier.show_informative_features(5)  


if __name__ == "__main__":
	parser = argparse.ArgumentParser()
	group = parser.add_mutually_exclusive_group(required=True)
	group.add_argument("-t","--train",nargs=1,help="File path to train the classifier")
	group.add_argument("-c","--classify",nargs=1,help="Text to be classified")
	group.add_argument("-x","--test-accuracy",nargs=1,help="Test Accuracy")

	args = parser.parse_args()

	if args.train:
		train(args.train[0])
	elif args.classify:
		classify_text(args.classify[0])
	elif args.test_accuracy:
		train_n_test(args.test_accuracy[0])
