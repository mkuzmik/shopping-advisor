import numpy as np
import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_selection import chi2
from sklearn.linear_model import LogisticRegression


def classify(training_data, test_data):
    df = pd.DataFrame(training_data + test_data)

    X_train = df.description
    y_train = df.feedback

    count_vect = CountVectorizer()
    X_counts = count_vect.fit_transform(df.description)
    tfidf_transformer = TfidfTransformer()
    X_tfidf = tfidf_transformer.fit_transform(X_counts)

    X_train = X_tfidf[:len(training_data)]
    X_test = X_tfidf[len(training_data):]
    y_train = df.feedback.tolist()[:len(training_data)]

    clf = LogisticRegression().fit(X_train, y_train)
    # clf = MultinomialNB().fit(X_train_tfidf, y_train)
    return clf.predict_proba(X_test)

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
