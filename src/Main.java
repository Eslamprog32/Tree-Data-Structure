import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
    static class Tree {
        private ArrayList<Integer> distinctElements;
        private SegmentTree seg;
        private int size;

        public Tree(int a[]) {
            distinctElements = new ArrayList<>();
            Arrays.sort(a);
            distinctElements.add(a[0]);
            for (int i = 1; i < a.length; i++) {
                if (a[i] != distinctElements.get(distinctElements.size() - 1)) {
                    distinctElements.add(a[i]);
                }
            }
            Collections.sort(distinctElements);
            size = distinctElements.size() + 5;
            seg = new SegmentTree(size);
        }

        public void add(int val) {
            int idx = getIndex(val);
            seg.updateIndex(1, 1, size, idx, 1);
        }

        private int getIndex(int val) {
            int max = distinctElements.size() - 1;
            int min = 0;
            int ans = -1;
            while (max >= min) {
                int mid = (max + min) / 2;
                if (distinctElements.get(mid) == val) {
                    return mid + 2;
                } else if (distinctElements.get(mid) > val) {
                    max = mid - 1;
                } else {
                    min = mid + 1;
                }
            }
            return ans;
        }

        public int countNumbersGreaterThan(int val) {
            int idx = getIndex(val);
            return seg.sumInRange(1, 1, size, idx + 1, size);
        }

        public int countNumbersLessThan(int val) {
            int idx = getIndex(val);
            return seg.sumInRange(1, 1, size, 1, idx - 1);
        }
        private static class SegmentTree {

            int size;
            int seg[];
            int arr[];

            public SegmentTree(int size) {
                this.size = size;
                seg = new int[size * 4];
            }

            void updateIndex(int idx, int s, int e, int ind, int val) {
                if (ind < s || ind > e) {
                    return;
                }
                if (s == ind && ind == e) {
                    seg[idx] += val;
                    return;
                }
                updateIndex(idx << 1, s, (s + e) / 2, ind, val);
                updateIndex(idx << 1 | 1, (s + e) / 2 + 1, e, ind, val);
                seg[idx] = seg[idx << 1] + seg[idx << 1 | 1];
            }

            int sumInRange(int idx, int s, int e, int l, int r) {
                if ((l > e) || s > r) {
                    return 0;
                }
                if (s >= l && e <= r) {
                    return seg[idx];
                }
                return sumInRange(idx << 1, s, (s + e) / 2, l, r) + sumInRange(idx << 1 | 1, (s + e) / 2 + 1, e, l, r);
            }
        }
    }

}