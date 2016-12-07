//
//  User.h
//  QuickHelp
//
//  Created by Mark on 12/6/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface User : NSDictionary

@property(readonly, nonatomic) NSString* username;
@property(readonly, nonatomic) NSString* password;
@property(readonly, nonatomic) NSArray* serviceList;



@end
