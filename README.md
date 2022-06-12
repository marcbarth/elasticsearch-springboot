# Getting Started

This is a simple elasticsearch spring boot example that demonstrate some basic functions of elastic search like "multi term search",
"fuzzy search","phonetic search" and "index switching".
In the test/resources you will find some different rest posts. 

# Install Elasticsearch DockerImage

docker-compose -f docker/docker-compose.yml up &

# Phonetic encoders

### Metaphone, Double Metaphone, and Metaphone 3 :
suitable for use with most English words, not just names. Metaphone algorithms are the basis for many popular spell checkers. The Double Metaphone phonetic encoding algorithm is the second generation of this algorithm.

### Soundex: 
which was developed to encode surnames for use in censuses. Soundex codes are four-character strings composed of a single letter followed by three numbers.

### Daitch–Mokotoff Soundex:
which is a refinement of Soundex designed to better match surnames of Slavic and Germanic origin. Daitch–Mokotoff Soundex codes are strings composed of six numeric digits.

### Cologne phonetics :
This is similar to Soundex, but more suitable for German words.

### New York State Identification and Intelligence System (NYSIIS): 
which maps similar phonemes to the same letter. The result is a string that can be pronounced by the reader without decoding.

### Match Rating Approach developed by Western Airlines in 1977: 
this algorithm has an encoding and range comparison technique.

### Caverphone: 
created to assist in data matching between late 19th century and early 20th century electoral rolls, optimized for accents present in parts of New Zealand
