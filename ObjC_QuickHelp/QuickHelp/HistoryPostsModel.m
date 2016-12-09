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
@property(strong, nonatomic) NSString* filePath;

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
- (instancetype)init {
    self = [super init];
    if (self) {
        // Save plist to Documents folder
        NSArray* paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
        NSString* documentsDirectory = [paths objectAtIndex:0];
        self.filePath = [documentsDirectory stringByAppendingString:@"historyPosts.plist"];
        NSMutableArray* readHistoryPosts = [NSMutableArray arrayWithContentsOfFile:self.filePath];
        if(!readHistoryPosts) {
           self.myPosts = [[NSMutableArray alloc] init];
        } else {
            self.myPosts = [[NSMutableArray alloc] init];
            NSDictionary* tempPost;
            for(tempPost in readHistoryPosts) {
                [self.myPosts addObject:tempPost];
            }
        }
    }
    return self;
}

// Add a post
-(void) addPost:(NSDictionary*) post {
    [self.myPosts addObject:post];
    [self save];
}

// Get number of posts
-(NSUInteger) getNumPosts {
    return [self.myPosts count];
}

// Get post at index
-(NSDictionary*) getPostAtIndex:(NSUInteger) index {
    return [self.myPosts objectAtIndex:index];
}

// Save array to file
-(void) save {
    NSMutableArray* myHistoryPosts = [[NSMutableArray alloc] init];
    for(NSDictionary* post in self.myPosts) {
        [myHistoryPosts addObject:post];
    }
    [myHistoryPosts writeToFile:self.filePath atomically:YES];
}
@end
