(ns pokeapi.adapter-test 
  (:require
   [pokeapi.adapter]
   [matcher-combinators.test :refer [match?]]
   [clojure.test :refer [deftest is testing]]))

(deftest data->payload-test!
  (testing "Should return valid pokemon data"
    (is (match?
         {:abilities ["imposter" "limber"]}
         (pokeapi.adapter/data->payload {"abilities"
                                         [{"ability" {"name" "limber", "url" "https://pokeapi.co/api/v2/ability/7/"}, "is_hidden" false, "slot" 1}
                                          {"ability" {"name" "imposter", "url" "https://pokeapi.co/api/v2/ability/150/"}, "is_hidden" true, "slot" 3}],}))))
  (testing "Should return abilities empty when is NOT valid pokemon data"
    (is (match?
         {:abilities []}
         (pokeapi.adapter/data->payload {})))))
