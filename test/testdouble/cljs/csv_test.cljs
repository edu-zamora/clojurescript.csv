(ns testdouble.cljs.csv-test
  (:require [testdouble.cljs.csv :as csv]
            [cljs.test :as t])
  (:require-macros [cljs.test :refer [deftest testing is run-tests]]))

(enable-console-print!)

(deftest write-csv-test
  (let [data [[1 2 3] [4 5 6]]]
    (testing "default separator ','"
      (is (= "1,2,3\n4,5,6" (csv/write-csv data))))

    (testing "user defined separator '|'"
      (is (= "1|2|3\n4|5|6" (csv/write-csv data :separator "|"))))

    (testing "user defined newline ':cr+lf'"
      (is (= "1,2,3\r\n4,5,6" (csv/write-csv data :newline :cr+lf))))

    (testing "user defined separator '|' and newline ':cr+lf"
      (is (= "1|2|3\r\n4|5|6" (csv/write-csv data :separator "|" :newline :cr+lf))))

    (testing "quote each field"
      (is (= "\"1,000\",\"2\",\"3\"\n\"4\",\"5,000\",\"6\"" (csv/write-csv [["1,000" "2" "3"] ["4" "5,000" "6"]] :quote? true))))

    (testing "error when newline is not one of :lf OR :cr+lf"
      (is (thrown-with-msg? js/Error #":newline" (csv/write-csv data :newline "foo"))))))

(deftest read-csv-test
  (let [data [["1" "2" "3"] ["4" "5" "6"]]]
    (testing "default separator ','"
      (is (= data (csv/read-csv "1,2,3\n4,5,6"))))

    (testing "user defined separator '|'"
      (is (= data (csv/read-csv "1|2|3\n4|5|6" :separator "|"))))

    (testing "user defined newline ':cr+lf'"
      (is (= data (csv/read-csv "1,2,3\r\n4,5,6" :newline :cr+lf))))

    (testing "user defined separator '|' and newline ':cr+lf'"
      (is (= data (csv/read-csv "1|2|3\r\n4|5|6" :separator "|" :newline :cr+lf))))))

(defn ^:export run []
  (run-tests))
