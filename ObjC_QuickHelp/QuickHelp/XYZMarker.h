//
//  XYZMarker.h
//  QuickHelp
//
//  Created by Mark on 12/5/16.
//  Copyright © 2016 Mark Vong. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <MapKit/MapKit.h>

// Lecture Slides
@interface XYZMarker : NSObject <MKAnnotation>
@property (copy, nonatomic) NSString *title;
@property (copy, nonatomic) NSString *subtitle;
@property (assign, nonatomic) CLLocationCoordinate2D coordinate;
@end
