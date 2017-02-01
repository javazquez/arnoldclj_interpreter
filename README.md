# arnoldclj_s
This project uses [Clojure](https://clojure.org/)

The project uses [Midje](https://github.com/marick/Midje/).

This project also uses[Instaparse](https://github.com/Engelberg/instaparse)

## How to run the tests

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.


Inspiration from https://github.com/lhartikk/ArnoldC





fizzbuzz example retrieved from 
https://raw.githubusercontent.com/chunfeilung/fizzbuzz/master/arnoldc/fizzbuzz.arnoldc
