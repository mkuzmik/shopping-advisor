from flask import Flask, request
from flask_restful import Resource, Api
from classification import *
import json

app = Flask(__name__)
api = Api(app)


class HelloWorld(Resource):
    def post(self):
        training_data = request.json['training_data']
        test_data = request.json['test_data']
        # print_most_correlated_terms(training_data)
        return classify(training_data, test_data).ravel().tolist()[1::2]

api.add_resource(HelloWorld, '/')


if __name__ == '__main__':
    app.run()
