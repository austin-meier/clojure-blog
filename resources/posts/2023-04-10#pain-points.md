# Creating this blog
<p style="color: #676767;">April 10th, 2023</p>

## The Process
In an attempt to build something actually useful with Clojure I am created a blog! The Clojure project is static website blog generator that will parse markdown files in a directory and convert them to HTML. 


## Some issues encountered along the way
### 1) Dependencies
I feel I am not sure quite how dependencies work and how I can navigate them without just strictly looking at what the github tells me. For instance markdown-clj is on clojars as just that `markdown-clj` but you pull it in as `markdown.core`. I feel like I do remember getting to something one time that showed me like all the current "environment" items available in the REPL/project. But I didn't get much feedback from leinigen after adding markdown-clj to the :dependencies list. I ended up running `lein deps` and I think that helped but I kinda was taking shots in the dark for a little moment.

### 2) Date sorting the posts
This ended up being a lot more thought provoking of an issue then I originally thought it would be. I needed to sort my blog posts by date to build them in the correct order. I first tried to see if I could use some file metadata but honestly decided to go the more simple route of dedicating the first line of the blog post to a timestamp and just parsing it as such and sorting the posts by them.

The issue with this then became finding a good way to parse the first line in to the keyword of a map and then the rest of the lines into a another keyword of the map. I ended up using the BufferedReader of Java with io/reader and line-seq but this gives a lazy sequence so I think I had to realize the sequence with vector before doing this. This also made it so I had to then string/join the rest of the vector which sort of feels inefficient to parse the whole thing in to a vector and then join it back to as string but I am not sure if there is a better way to go about this.



### 3) I don't actually know markdown well
There seems to be like different versions of it. I only really have experience with GitHub markdown. Thankfully this appears to support HTML so that is a big plus.
<img src="https://cdn.discordapp.com/attachments/565694425674678273/1010803488269287424/IMG_3574.jpg"  width="20%" height="20%">

### 4) How to blog?
- ???
- ???
