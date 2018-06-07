# Shopping Advisor

## Overview

An intelligent shopping assistant, who helps you to find best and most suitable to your needs offers.

## Endpoints

- `/search?q=SEARCH_PHRASE` - search for offers related to given SEARCH_PHRASE
- `/recommendation/run` - run algorithms finding the most suitable sales offers depending on products searched before by user
- `/preferences?mail=EMAIL_ADDRESS` - returns the list of sales offers with numbers indicating the matching level of each offer to the user with given EMAIL_ADDRESS

## Usage

Once the application is running, the user can enter the main page at `/` and enter the search phrase for the product he is looking for into the input box.
After that he has to assess 6 offer he likes (the offers that match his query the best) and 6 he doesn't like.
The assessed offers are sent to the backend along with the provided user's email address. 

When it is done the `/recommendation/run` endpoint can be executed.
Then the user gets an email containing 3 links to offers that match him the best (according to the algorithm).

Except for email, you can check the offers assessed by the algorithm through the `/preferences?mail=EMAIL_ADDRESS` endpoint.  

# Developers guide

## Postgres initialization
```sql
CREATE USER root WITH PASSWORD 'advisor';
CREATE DATABASE advisor OWNER root;
```

### Secrets maintenance

All secrets (passwords, web api keys etc.) are stored in `src/main/resources/secrets.properties` file. This file is 
marked as ignored in `/gitignore`. So as to run this app with you own secrets please rename `src/main/resources/secrets.properties-TEMPLATE`
to `src/main/resources/secrets.properties` and fill it with your own passwords and keys.

# Technologies

The application consists of the technologies listed below.

## Frontend

- AngularJS 1.6.9

## Backend (app)

- Java 8
- SpringBoot
- jsoup (the HTML parser)
- Allegro WebAPI
- PostgreSQL

## Backend (ML)

- Python
- Flask