
/// read some code and write a conscise highlivel english description of codes actions

public static List<Integer> mystery(List<Integer> l, int i) {
    List<Integer> r = new ArrayList<>();
    for (int j = i; j < l.size(); j++) {
      r.add(l.get(j));
    }
    return r;
  }
  // returns list of all elements after index i of the parameter list l



  //identify logic error
  /** 
  * Swaps the `i`th and `j`th elements of the List `l`. 
  * Assume 0 <= i <= j <= l.size().   
  */
public static void swap(List<Integer> l, int i, int j) {
    l.set(i, l.get(j));
    l.set(j, l.get(i));
  }

  ///references i after is is reset so it would double up j/not change j





  ///write a short class or method according to a textual description
  //Write a method static List<Integer> concatenate(List<Integer> l1, List<Integer> l2). 
  //concatenate should return, as a new list, all the elements of l1 followed by all 
  //the elements of l2, in the order they appeared. Your code must not modify l1 or l2.

  ///For example, with an input of l1 = [1, 2, 5], l2 = [2, 4, 1], it should return the list [1, 2, 5, 2, 4, 1].
  
  //Assume List and ArrayList are correctly imported.

  public static List<Integer> concatenate(List<Integer> l1, List<Integer> l2) {
    List<Integer> numbers = new ArrayList<>();

    for(int n:l1){
        numbers.add(n);
    }
    for(int n:l2){
        numbers.add(n);
    }
return numbers;
    
  }

  //this owuld also work
  public static List<Integer> concatenate(List<Integer> l1, List<Integer> l2) {
  
List<Integer> r = new ArrayList<>(l1);
r.addAll(l2);
}