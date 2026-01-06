package main

import "fmt"

// twoSum 使用 map 实现 O(n) 时间复杂度
func twoSum(nums []int, target int) []int {
	m := make(map[int]int) // value -> index

	for i, v := range nums {
		if idx, ok := m[target-v]; ok {
			return []int{idx, i}
		}
		m[v] = i
	}
	return nil
}

func main() {
	nums := []int{2, 7, 11, 15}
	target := 9

	result := twoSum(nums, target)

	if result != nil {
		fmt.Printf("nums = %v\n", nums)
		fmt.Printf("target = %d\n", target)
		fmt.Printf("result = %v\n", result)
		fmt.Printf("nums[%d] + nums[%d] = %d\n",
			result[0],
			result[1],
			nums[result[0]]+nums[result[1]],
		)
	} else {
		fmt.Println("No solution found")
	}
}
