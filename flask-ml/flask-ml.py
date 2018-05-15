from flask import Flask, request
from flask_restful import Resource, Api
from classification import *
import json

app = Flask(__name__)
api = Api(app)


class HelloWorld(Resource):
    def post(self):
        training_data = request.json['training_offers']
        test_data = request.json['test_offers']
        # print_most_correlated_terms(training_data)
        output = classify(training_data, test_data).ravel().tolist()[1::2]
        result = {'asserted_offers': []}
        for data, prediction in zip(test_data, output):
            result['asserted_offers'].append({'url': data['url'], 'feedback': prediction})
        return result

api.add_resource(HelloWorld, '/')


if __name__ == '__main__':
    app.run()
