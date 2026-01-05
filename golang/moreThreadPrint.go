package main

import (
	"fmt"
	"sync"
)

func main() {
	var wg sync.WaitGroup
	wg.Add(3)

	chA := make(chan struct{})
	chB := make(chan struct{})
	chC := make(chan struct{})

	go func() {
		defer wg.Done()
		for i := 0; i < 10; i++ {
			<-chA
			fmt.Print("A")
			if i != 9 {
				chB <- struct{}{}
			}
		}
	}()

	go func() {
		defer wg.Done()
		for i := 0; i < 10; i++ {
			<-chB
			fmt.Print("B")
			if i != 9 {
				chC <- struct{}{}
			}
		}
	}()

	go func() {
		defer wg.Done()
		for i := 0; i < 10; i++ {
			<-chC
			fmt.Print("C")
			if i != 9 {
				chA <- struct{}{}
			}
		}
	}()

	// 启动
	chA <- struct{}{}
	wg.Wait()
}
