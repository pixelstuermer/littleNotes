[![Build Status](https://travis-ci.org/pixelstuermer/littleNotes.svg?branch=master)](https://travis-ci.org/pixelstuermer/littleNotes)
[![codecov](https://codecov.io/gh/pixelstuermer/littleNotes/branch/master/graph/badge.svg)](https://codecov.io/gh/pixelstuermer/littleNotes)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![GitHub release](https://img.shields.io/github/release/pixelstuermer/littleNotes.svg)]()

# littleNotes
Small MongoDB application with basic CRUD operations for something like notes

## SwaggerUI
The SwaggerUI for detailled API description can be found here:

    {base-url}/swagger-ui.html

## Config Variables
To connect to a MongoDB database, the following variable has to be set:

    spring.data.mongodb.uri

For example like this:

    spring.data.mongodb.uri=mongodb://user:password@domain:port/database

For connection to a (notes-) collection, the following variable has to be set:

    collection.name

For example like this:

    collection.name=notes

Further on, the following passwords can ge granted when launching the application:

    admin.password={...}
    user.password={...}
    phil.password={...}