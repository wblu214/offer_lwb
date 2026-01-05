package main

import (
	"fmt"
	"sort"
)

func mergeIntervals(intervals [][]int) [][]int {
	if len(intervals) == 0 {
		return nil
	}

	// 1) 按区间起点升序；起点相同按终点升序
	sort.Slice(intervals, func(i, j int) bool {
		if intervals[i][0] == intervals[j][0] {
			return intervals[i][1] < intervals[j][1]
		}
		return intervals[i][0] < intervals[j][0]
	})

	// 2) 扫描合并
	res := make([][]int, 0, len(intervals))
	curStart, curEnd := intervals[0][0], intervals[0][1]

	for i := 1; i < len(intervals); i++ {
		s, e := intervals[i][0], intervals[i][1]

		if s <= curEnd { // 有重叠/可合并
			if e > curEnd {
				curEnd = e
			}
		} else { // 不重叠，先结算当前区间
			res = append(res, []int{curStart, curEnd})
			curStart, curEnd = s, e
		}
	}
	// 3) 别忘了最后一个
	res = append(res, []int{curStart, curEnd})

	return res
}

func main() {
	in := [][]int{{1, 3}, {2, 6}, {8, 10}, {15, 18}}
	out := mergeIntervals(in)
	fmt.Println(out) // [[1 6] [8 10] [15 18]]
}
