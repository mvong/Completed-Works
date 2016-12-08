//
//  HistoryPostsModel.h
//  QuickHelp
//
//  Created by Mark on 12/8/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Post.h"

@interface HistoryPostsModel : NSObject
@property(readonly, nonatomic) NSMutableArray* myPosts;
@property(readonly, nonatomic) NSDictionary* thisPost;

// Creating history posts model
+(instancetype) sharedHistoryPostsModel;

// Add post to model
-(void) addPost:(NSDictionary*) post;

// Get number of posts
-(NSUInteger) getNumPosts;

// Get post at index
-(NSDictionary*) getPostAtIndex:(NSUInteger) index;
@end
