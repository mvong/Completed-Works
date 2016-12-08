//
//  HistoryPostsModel.m
//  QuickHelp
//
//  Created by Mark on 12/8/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import "HistoryPostsModel.h"
@interface HistoryPostsModel()

@property(readwrite, nonatomic) NSMutableArray* myPosts;

@end
@implementation HistoryPostsModel

//General History Singleton
+(instancetype) sharedHistoryPostsModel {
    static HistoryPostsModel* _sharedHistoryModel = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _sharedHistoryModel = [[self alloc] init];
    });
    return _sharedHistoryModel;
}

// Initialize array of posts
- (instancetype)init
{
    self = [super init];
    if (self) {
        self.myPosts = [[NSMutableArray alloc] init];
    }
    return self;
}

// Add a post
-(void) addPost:(NSDictionary*) post {
    [self.myPosts addObject:post];
}

// Get number of posts
-(NSUInteger) getNumPosts {
    return [self.myPosts count];
}

// Get post at index
-(NSDictionary*) getPostAtIndex:(NSUInteger) index {
    return [self.myPosts objectAtIndex:index];
}
@end
