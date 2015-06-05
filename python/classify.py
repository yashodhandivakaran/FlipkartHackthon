from textblob.classifiers import NaiveBayesClassifier
import argparse
import utils
import data

CLASSIFIER_FILE_NAME = 'classifier.pickle'

def load_data(file_path):
	""" Read the file into textblob understandable format"""
	with open(file_path) as f:
		data = []
		for line in f:
			line = line.decode('utf-8')
			if line:
				data.append(tuple(line.rsplit(',',1)))
	return data

def get_features(document):
	document_words = set(document)
	features = {}
	for word in data.wordlist:
		features['contains({})'.format(word)] = (word in document_words)
	return features

def train(file_path):
	""" train the classifier using the provided file """
	data = load_data(file_path)
	#classifier = NaiveBayesClassfier(data,feature_extractor=get_features)
	classifier = NaiveBayesClassifier(data)
	utils.store_classifier(classifier,CLASSIFIER_FILE_NAME)

def classify_text(text):
	 classifier = utils.load_classifier(CLASSIFIER_FILE_NAME)
	 print classifier.classify(text)


if __name__ == "__main__":
	parser = argparse.ArgumentParser()
	group = parser.add_mutually_exclusive_group(required=True)
	group.add_argument("-t","--train",nargs=1,help="File path to train the classifier")
	group.add_argument("-c","--classify",nargs=1,help="Text to be classified")

	args = parser.parse_args()

	if args.train:
		train(args.train[0])
	elif args.classify:
		classify_text(args.classify[0])
