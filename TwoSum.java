import java.util.Arrays;
import java.util.HashMap;



class Solution{

    public static void main(String[] args) {
        int [] input = {3, 4, 5, 6};
        int target = 7;
        System.out.println(Arrays.toString(twoSum(input, target)));
        System.out.println(Arrays.toString(hashTwoSum(input, target)));

    }


    public static int[] twoSum (int[] nums, int target) {
        int left = 0;
        int right = nums.length -1;
        
        while (left <= right) {
            int s = nums[left] + nums[right];
            
            if (s == target) {
                return new int[]{nums[left], nums[right]};
            } else if (s<target) {
                left++;
            } else if (s > target){
                right--;
            }
        }
        return new int[]{-1, -1};
    }

    public static int[] hashTwoSum(int[] nums, int target) {
        HashMap<Integer, Integer> numToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if(numToIndex.containsKey(complement)){
                return new int[]{numToIndex.get(complement), i};
            }

            numToIndex.put(nums[i], i);

        }
        
        return new int[]{};
    }
}