package alltool.aa面试题.力扣算法题;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author lizhong.liu
 * @version TODO
 * @class ??
 * @CalssName 算法题
 * @create 2020-10-13 15:35
 * @Des TODO
 */
public class 算法题 {
    @Test  // 1. 两数之和
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; i++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    @Test  // 2. 两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(-1);
        ListNode pro = result;
        int t = 0;
        while (l1 != null || l2 != null || t != 0) {
            if (l1 != null) {
                t += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                t += l2.val;
                l2 = l2.next;
            }
            pro.next = new ListNode(t % 10);
            pro = pro.next;
            t = t / 10;
        }
        return result.next;
    }

    @Test // 3. 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        int chang = 0;
        if (s.length() > 0) chang = 1;
        for (int i = 0; i < s.length() - 1; i++) {
            String strtmp = s.charAt(i) + "";
            for (int j = i + 1; j < s.length(); j++) {
                if (strtmp.contains(s.charAt(j) + "")) {
                    if (strtmp.length() > chang) {
                        chang = strtmp.length();
                    }
                    break;
                } else {
                    strtmp += s.charAt(j);
                    if (strtmp.length() > chang) {
                        chang = strtmp.length();
                    }
                }

            }
        }
        return chang;
    }

    @Test // 4. 寻找两个正序数组的中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
//        int[] nums = new int[nums1.length+nums2.length];
//        for (int i = 0; i < nums1.length; i++) {
//            nums[i] = nums[i];
//        }
//        for (int i = 0; i < nums2.length; i++) {
//            nums[i+nums1.length] = nums[i];
//        }
        int[] nums = ArrayUtils.addAll(nums1, nums2);
        Arrays.sort(nums);
        int length = nums.length;
        if (length % 2 == 0) {
            return (double) (nums[length / 2] + nums[length / 2 - 1]) / 2;
        } else {
            return (double) nums[length / 2];
        }
    }

    @Test // 5. 最长回文子串
    public String longestPalindrome(String s) {
        int length = s.length();
        for (int i = length; i > 1; i--) {
            for (int j = 0; j < length - i + 1; j++) {
                String substring = s.substring(i, j + length - 1);
                if (huiwen(substring)) {
                    return substring;
                }
            }
        }
        return s.charAt(0) + "";
    }

    public Boolean huiwen(String str) {
        for (int i = 0; i <= str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    @Test // 6. Z 字形变换
    public String convert(String s, int numRows) {
        String result = "";
        if (s.length() > 0) {
            for (int i = 0; i < s.length(); i = i + 4) {
                result += s.charAt(i);
            }
        }
        if (s.length() > 1) {
            for (int i = 1; i < s.length(); i = i + 2) {
                result += s.charAt(i);
            }
        }
        if (s.length() > 2) {
            for (int i = 2; i < s.length(); i = i + 4) {
                result += s.charAt(i);
            }
        }
        return result;
    }
}
