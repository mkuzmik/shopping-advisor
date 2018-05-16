from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_selection import chi2
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.naive_bayes import MultinomialNB
from sklearn.linear_model import LogisticRegression
from sklearn.svm import LinearSVC


def classify(training_data, test_data):
    df = pd.DataFrame(training_data)

    X_train = df.description
    y_train = df.feedback

    count_vect = CountVectorizer()
    X_train_counts = count_vect.fit_transform(df.description)
    tfidf_transformer = TfidfTransformer()
    X_train_tfidf = tfidf_transformer.fit_transform(X_train_counts)

    clf = LogisticRegression(random_state=0).fit(X_train_tfidf, y_train)
    return clf.predict_proba(count_vect.transform(map(lambda data: data['description'], test_data)))

def build_features(dataset):
    tfidf = TfidfVectorizer(sublinear_tf=True, min_df=1, norm='l2', encoding='latin-1', ngram_range=(1, 2), stop_words='english')
    df = pd.DataFrame(dataset)
    features = tfidf.fit_transform(df.description).toarray()
    labels = df.feedback
    return features, labels, tfidf

def print_most_correlated_terms(dataset):
    features, labels, tfidf = build_features(dataset)
    for label in labels:
        features_chi2 = chi2(features, labels)
        indices = np.argsort(features_chi2[0])
        feature_names = np.array(tfidf.get_feature_names())[indices]
        unigrams = [v for v in feature_names if len(v.split(' ')) == 1]
        bigrams = [v for v in feature_names if len(v.split(' ')) == 2]
        print("# '{}':".format(label))
        print("  . Most correlated unigrams:\n. {}".format('\n. '.join(unigrams[:])))
        print("  . Most correlated bigrams:\n. {}".format('\n. '.join(bigrams[:])))
