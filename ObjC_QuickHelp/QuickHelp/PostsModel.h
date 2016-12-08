//
//  PostsModel.h
//  QuickHelp
//
//  Created by Mark on 12/8/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Post.h"

@interface PostsModel : NSObject

@property(readonly, nonatomic) NSMutableArray* myPosts;
@property(readonly, nonatomic) NSDictionary* thisPost;
@property(readwrite, nonatomic) NSUInteger currentPost;

// Creating posts model
+(instancetype) sharedPostsModel;

// Add post to model
-(void) addPost:(Post*) post;

// Get number of posts
-(NSUInteger) getNumPosts;

// Get post at index
-(Post*) getPostAtIndex:(NSUInteger) index;

@end
