# giphy-web-api
WebAPI application that allows to generate and save personalized GIFs.


The application consists of 2 parts: file storage on disk, and a personalized in-memory cache (dictionary):

```
{
 "john": [{
   "ball": [
     "/path/to/1.gif",
     "/path/to/2.gif",
     "/path/to/3.gif",
   ],
   "moon": [
     "/path/to/1.gif",
     "/path/to/2.gif",
     "/path/to/3.gif",
   ]
 }],
 "jack": [{
   "mad": [
     "/path/to/1.gif",
     "/path/to/2.gif",
     "/path/to/3.gif",
   ]
 }]
}
```


The application has two main functions:

### GIF Generation
1. The user submits a request to generate a GIF. The user must specify an id - it can be any string that can be used as a path in the OS (basic validation by id must be added), as well as a keyword by which the GIF can be found.
2. The application tries to get the path to the GIF from the cache on the disk (cache folder) (if there are several suitable GIFs, then choose a random one) and add it to the user folder (by id). Then refresh the data in the cache (in memory) and return the path to the GIF to the user.
3. If the application did not find a GIF on disk (cache folder), then you need to make a request to giphy.com, save the GIF (you can use the Id from the response from giphy.com or UUID as the file name) to the disk cache (cache folder) and execute step 2.
4. Add generation record to user history file history.csv

```
10-10-2020,mad,/path/to/1.gif
10-12-2020,ball,/path/to/2.gif
```

### GIF search
1. The user submits a GIF search request. The user must specify an id - it can be any string that can be used as a path in the OS (basic validation by id must be added), as well as a keyword by which the GIF can be found.
2. The application tries to get the path to the GIF from the cache in memory (if there are several, then choose a random one) and return it to the user.
3. If there is no suitable GIF in memory, then try to find it in the user folder and follow step 2.
4. If there is no suitable GIF in the user folder, return 404.

### Important:
* Added two types of config: dev and prod. In the prod version, do not store the API Key for GIPHY.
* Used DI for all elements of the web application.
* Added validation for the X-BSA-GIPHY header to the application (the value can be anything): if the header is absent, return 403.
* Every user request is logged.

# FS structure
```
bsa_giphy
 cache
   ball
     1.gif
     2.gif
     3.gif
   moon
     1.gif
     2.gif
     3.gif
 users
   john
     history.csv
     ball
       1.gif
       2.gif
       3.gif
   jack
     history.csv
     moon
       1.gif
       2.gif
       3.gif
```

# API

Get cache from disk. If query is specified, then select only the corresponding files

```
GET /cache?query

Response

[
 {
   "query": "ball",
   "gifs": [
     "/path/to/1.gif",
     "/path/to/2.gif",
     "/path/to/3.gif",
   ]
 },
 {
   "query": "mad",
   "gifs": [
     "/path/to/1.gif,
     "/path/to/2.gif,
     "/path/to/3.gif,
   ]
 },
 {
   "query": "moon",
   "gifs": [
     "/path/to/1.gif",
     "/path/to/2.gif",
     "/path/to/3.gif",
   ]
 }
]
```

Download the picture from giphy.com and put it in the corresponding folder in the cache on the disk. Return an object with all pictures in the cache on disk.

```
POST /cache/generate

Body

{
 "query": "moon"
}
Response

{
 "query": "moon",
 "gifs": [
   "/path/to/1.gif",
   "/path/to/2.gif",
   "/path/to/3.gif",
 ]
}
```

Clear disk cache

```
DELETE /cache
```

Get a list of all files without linking to keywords

```
GET /gifs

Response

[
 "/path/to/1.gif",
 "/path/to/2.gif",
 "/path/to/3.gif",
 "/path/to/4.gif"
]
```

Get a list of all files from a user folder on disk

```
GET /user/:id/all

Response

[
   {
     "query": "ball",
     "gifs": [
       "/path/to/1.gif",
       "/path/to/2.gif",
       "/path/to/3.gif",
     ]
   },
   {
     "query": "moon",
     "gifs": [
       "/path/to/1.gif",
       "/path/to/2.gif",
       "/path/to/3.gif",
     ]
   }
]
```

Get User Story

```
GET /user/:id/history

Response

[
 {
   "date": "10-10-2020",
   "query": "mad",
   "gif": "/path/to/1.gif"
 },
 {
   "date": "10-11-2020",
   "query": "ball",
   "gif": "/path/to/2.gif"
 },
 {
   "date": "10-12-2020",
   "query": "moon",
   "gif": "/path/to/3.gif"
 },
]
```

Clear user history

```
DELETE /user/:id/history/clean
```

Search for a picture. If the force parameter is specified, then ignore the in-memory cache and immediately read data from disk. Add a file to the in-memory cache if it is not already there.

```
GET /user/:id/search?query&force

Response

"/path/to/1.gif"
```

Generate GIF. If the force parameter is specified, then ignore the disk cache (cache folder) and immediately search for the file in giphy.com. Add file to disk cache (cache folder).

```
POST /user/:id/generate

BODY

{
 "query": "ball",
 "force": true
}
RESPONSE

"/path/to/1.gif"
```

Clear the user's cache in memory using the query key. If the key is not specified, then clear all data for the user in the cache in memory.

```
DELETE /user/:id/reset?query
```

Delete all data by user both on disk and in memory cache

```
DELETE /user/:id/clean
```
