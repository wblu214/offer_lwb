package main

import "fmt"

type ListNode struct {
	Val  int
	Next *ListNode
}

// ReverseListIter 迭代反转链表
func ReverseListIter(head *ListNode) *ListNode {
	var prev *ListNode = nil
	cur := head

	for cur != nil {
		next := cur.Next // 先保存下一个
		cur.Next = prev  // 反转指针
		prev = cur       // prev 前进
		cur = next       // cur 前进
	}
	return prev
}

func buildList(nums []int) *ListNode {
	dummy := &ListNode{}
	tail := dummy
	for _, v := range nums {
		tail.Next = &ListNode{Val: v}
		tail = tail.Next
	}
	return dummy.Next
}

func printList(head *ListNode) {
	for p := head; p != nil; p = p.Next {
		fmt.Print(p.Val)
		if p.Next != nil {
			fmt.Print(" -> ")
		}
	}
	fmt.Println()
}

func main() {
	head := buildList([]int{1, 2, 3, 4, 5})
	printList(head)

	head = ReverseListIter(head)
	printList(head)
}
