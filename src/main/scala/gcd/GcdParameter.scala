package chipv.gcd

import chisel3._
import org.chipsalliance.cde.config._

case object width extends Field[Int]

class GcdConfig extends Config((site, here, up) => {
   case width => 2
})
