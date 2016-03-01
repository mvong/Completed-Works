#include "llistint.h"
#include "gtest/gtest.h"

class LListTest : public testing::Test {
protected: 

	LListTest() {

	}

	virtual ~LListTest() {

	}
	// Set up the two linkedlists
	virtual void SetUp() {
		L.insert(0, 5);
		L.insert(1, 10);
		L.insert(2, 15);
		tempL.insert(0, 1);
		tempL.insert(1, 2);
		tempL.insert(2, 3);

	}

	virtual void TearDown() {

	}
	// Create linked lists
	LListInt L;
	LListInt tempL;
};
 // Test if copy constructor functions correctly
 TEST_F(LListTest, CopyNominal) {
 	LListInt test(L);
 	EXPECT_EQ(test.get(0), 5);
 	EXPECT_EQ(test.get(1), 10);
 	EXPECT_EQ(test.get(2), 15);
 }
// Test if assignment operator functions correctly
TEST_F(LListTest, AssignNominal) {
	LListInt test;
	test.insert(0, 20);
	test.insert(1, 25);
	test.insert(2, 30);
	test = tempL;
	EXPECT_EQ(test.get(0), 1);
	EXPECT_EQ(test.get(1), 2);
	EXPECT_EQ(test.get(2), 3);

}
// Test if add operator functions correctly
TEST_F(LListTest, AddNominal) {
	LListInt addT;
	addT.insert(0, 500);
	addT.insert(1, 23);
	addT.insert(2, 35);
	addT = L + addT;
	EXPECT_EQ(addT.get(0), 500);
	EXPECT_EQ(addT.get(1), 23);
	EXPECT_EQ(addT.get(2), 35);
	EXPECT_EQ(addT.get(3), 5);
	EXPECT_EQ(addT.get(4), 10);
	EXPECT_EQ(addT.get(5), 15);


}