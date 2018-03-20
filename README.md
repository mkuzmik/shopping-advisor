# Shopping Advisor

## Overview

An intelligent shopping assistant, who helps you to find best and most suitable to your needs offers.

## Endpoints

- `/search?q=SEARCH_PHRASE` - search for offers related to given SEARCH_PHRASE

## Developers guide

### Secrets maintenance

All secrets (passwords, web api keys etc.) are stored in `src/main/resources/secrets.properties` file. This file is 
marked as ignored in `/gitignore`. So as to run this app with you own secrets please rename `src/main/resources/secrets.properties-TEMPLATE`
to `src/main/resources/secrets.properties` and fill it with your own passwords and keys.
