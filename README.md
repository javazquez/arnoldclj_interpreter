# arnoldclj_s

This project uses [Clojure](https://clojure.org/)

This project uses [Midje](https://github.com/marick/Midje/).

This project also uses[Instaparse](https://github.com/Engelberg/instaparse)

## How to run the tests

I had to add `[lein-midje "3.1.3"]` to my ~/.lein/profiles.clj

```clojure
{:user {:plugins [[cider/cider-nrepl "0.8.1"]
                  [lein-midje "3.1.3"]]}}
```

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.


Inspiration from https://github.com/lhartikk/ArnoldC





fizzbuzz example retrieved from 
https://raw.githubusercontent.com/chunfeilung/fizzbuzz/master/arnoldc/fizzbuzz.arnoldc
