# INFINIHEDRON

The Infinihedron is a large 12-faced polygon (dodecahedron) wiht half-mirroed faces and LEDs lining the edges. When illuminated, this creates the effect of infinite lights in a kalidescopic pattern.

[Here's a quick video of it in action](https://photos.app.goo.gl/cjdmcP2U8k5sayQv9), along with a glance at the software running it.
[More photos and videos](https://photos.app.goo.gl/n18Prc7EzE4qecTB7)

It stands 480mm tall (nearly 19"), has 360 RGB LEDs and consumes around 100 watts of power!

## Goals

Beyond looking cool, there were a few design goals for this project:

* Individually addressable LEDs - Most projects like this just repeat the same led pattern on all edges, which is cool, but I wanted to make more 3-dimensional effects
* Really good software support - I want to be able to play this thing like a DJ: Set a beat, choose an effect and a color palette, and then switch to other effects on the fly. Crucial to this is having software that makes it easy to create new effects and color palettes.

## Design

* Frame pieces modeled in SCAD and 3D printed in silver PLA
  * 30 edges, 200mm long
  * 20 vertices
* Panels are hand cut and filed acrylic (way too big for my laser cutter!)*
* "Neopixel" compatible LED strips
  * 12 per edge * 30 edges
  * 360 total RGB LEDs
* FadeCandy USB-to-Neopixel driver
* Raspberry Pi Zero W wireless controller
* Software in Processing (based on Java)
* Animations and UI running on Microsoft Surface Go 2 tablet
* Powered by 30A 5V supply, drawing around 18A on average

There's still a lot of work potentially left to do on the software, but it does work! I look forward to getting back to it soon.

* Cutting the acrylic panels was the greatest challenge in a very challenging project. I needed to construct 12 identical and near-perfect pentagons but each one is a little over 12 inches (307mm) at its widest point. Too big for my laser cutter, unbelievably expensive to have done professionally. Even printing out a "perfect" pentagon across multiple sheets of paper failed as I found that my laser printer actually has pretty poor dimensional accuracy. And if even one of these is off by maybe half a millimeter, there will be a very visible gap between the edges somewhere (and there is...). My eventual solution was to buy a pair of hilariously big digital calipers with a 12" (~300mm) capacity. I then made a circle with 170mm radius using the calipers, then constructed the edges by measuring 200mm between points on the circle. (I tried all this with ruler and straightedge first, it wasn't nearly precise enough, thus the calipers) It worked! Well enough. If I had to do it again I might try to find a makerspace or the like that has a large enough laser cutter.
