//
//  Post.h
//  QuickHelp
//
//  Created by Mark on 12/8/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Post : NSObject

@property(readonly, nonatomic) NSString* postDescription;
@property(readonly, nonatomic) NSString* address;
@property(readonly, nonatomic) NSString* city;
@property(readonly, nonatomic) NSString* zipcode;
@property(readonly, nonatomic) NSString* user;

// Initialize post object 
-(instancetype) initWithPostDescription: (NSString*) postDescription
                    address: (NSString*) address
                       city: (NSString*) city
                    zipcode: (NSString*) zipcode
                       user: (NSString*) user;
@end
