//
//  Post.m
//  QuickHelp
//
//  Created by Mark on 12/8/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import "Post.h"

@interface Post()

@property(readwrite, nonatomic) NSString* postDescription;
@property(readwrite, nonatomic) NSString* address;
@property(readwrite, nonatomic) NSString* city;
@property(readwrite, nonatomic) NSString* zipcode;
@property(readwrite, nonatomic) NSString* user;

@end

@implementation Post

// Initialize post object
-(instancetype) initWithPostDescription:(NSString *)postDescription
                    address:(NSString *)address
                       city:(NSString *)city
                    zipcode:(NSString *)zipcode
                       user:(NSString *)user {
    Post* newPost = [Post alloc];
    newPost.postDescription = postDescription;
    newPost.address = address;
    newPost.city = city;
    newPost.zipcode = zipcode;
    newPost.user = user;
    return newPost;
}

@end
