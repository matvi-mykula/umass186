
//// read code and write concise high level english description of the codes action ////////-------

double mystery(int[] a) {
  if (a.length == 0) {
    return 0.0;
  }

  double x = 0.0;
  for (int i: a) {
    x += i;
  }
  return x / a.length;
}
// takes argument of integer array a
// if the length of a is 0 then return 0
// initialize x as 0.0
// loop through each element of a and add it to x
// return the total x divided by the length of the array a
/// answer *** returns mean or 0 if a is empty.... duh
// my answer is not high level enough

int mystery(char c, String s) {
  int x = 0;
  for (char k : s.toCharArray()) {
    if (c != k) {
      x++;
    }
  }
  return x;
}
// takes arguments character c and string s
// intialize x as 0
// loop through each character in string s
// if the character is not c then increment x by 1
// return x
///*** returns number of cahracters in s that arent equal to c */


//// find conceptual error /////////////-------------------
/** Return the mean of the values stored in `a`. */
double mean(int[] a) {
  if (a.length == 0) {
    return 0.0;
  }
  double x = 0.0;
  for (int i = 0; i < a.length; i++) {
    x += i;
  }
  return x / a.length;
}
// x+=i is adding the index not the value at the index and is not relavent to finding the mean


/** Return the sum of the odd numbers from 0 to n, inclusive. */
int sumOddUpTo(int n) {
  int sum = 0;
  for (int i = 0; i <= n; i++) {
    if (i % 2 == 0) {
      break;
    }
    else {
      sum += i;
    }
  }
  return sum;
}
// if i is even its breaking out of loop, should be continue

///////------------
// Define a class Dog. A Dog instance has a name (which could change), 
// a breed (which is immutable, that is, it cannot change) and a licenseNumber 
// (an integer between 1 and 1,000,000, which is immutable). 
// Two Dog instances are considered equal if and only if their licenseNumbers are equal.

public class Dog {
    private String name;
    private final String breed;
    private final in licenseNumber;

    public Dog(String n, String b, int l){

        name = n;
        breed = b;
        licenseNumber = l;


    }
    public boolean equals(Dog o){
       return licenseNumber == o.licenseNumber;
    }
}



// ///
// Write a method which, given two int arrays, determines 
// whether the values in the first array a are a subset of 
// the values in the second array b. In other words, return 
// true if and only if every value in a is also present in b. 
// Your method should have the signature boolean isSubset(int[] a, int[] b).


boolean isSubset(int[] a, int[] b){

Map<String, Object> obj =  new HashMap<>();

for (var each : a) {
    obj.put(each, false);
}
for (var each : b) {
    if(obj.containsKey(each)){
        obj.put(each, true);
    }
}
for (boolean value : obj.values()) {
    if (!value){
        return false;
    }
}
return true;
}