(ns pokeapi.integration-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [pokeapi.integration]
   [matcher-combinators.test :refer [match? thrown-match?]]))

(deftest get-pokemon-test
  (testing "Should return an pokemon data"
    (is (match?
         {"name" "ditto"}
         (pokeapi.integration/get-pokemon "ditto"))))
  (testing "Should throw Exception"
    (is (thrown-match?
         {:body "Not Found"
          :status 404}
         (throw (pokeapi.integration/get-pokemon "non-existent-pokemon"))))))
