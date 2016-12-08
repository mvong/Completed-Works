//
//  PostsModel.m
//  QuickHelp
//
//  Created by Mark on 12/8/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import "PostsModel.h"


@interface PostsModel()

@property(readwrite, nonatomic) NSMutableArray* myPosts;
@property(readwrite, nonatomic) NSDictionary* thisPost;

@end

@implementation PostsModel

// Singleton
+(instancetype) sharedPostsModel {
    static PostsModel* _sharedModel = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _sharedModel = [[self alloc] init];
    });
    return _sharedModel;
}

// Initialize array of posts
- (instancetype)init
{
    self = [super init];
    if (self) {
        self.currentPost = 0;
        self.myPosts = [[NSMutableArray alloc] init];
    }
    return self;
}

// Get number of posts
-(NSUInteger) getNumPosts {
    return [self.myPosts count];
}

// Add a post
-(void) addPost:(Post*) post {
    [self.myPosts addObject:post];
}

// Get post at index
-(Post*) getPostAtIndex:(NSUInteger)index {
    //self.currentPost = index;
    return [self.myPosts objectAtIndex:index];
}

@end
