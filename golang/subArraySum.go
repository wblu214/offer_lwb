package main

import "fmt"

func main() {

	//var subArray = make([]int, 0)

	subArray01 := []int{1, 9, 2, 8, 9, 4, 6, 8}

	target := 10

	result := subArraySumK(subArray01, target)

	fmt.Println(result)
}

func subArraySumK(subs []int, k int) int {
	count := 0
	for i := 0; i < len(subs); i++ {
		sum := 0
		for j := i; j >= 0; j-- {
			sum += subs[j]
			if sum == k {
				count++
			}
		}
	}
	return count
}
