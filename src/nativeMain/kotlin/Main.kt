import kotlinx.cinterop.*
import libvirt.*

fun main() {
    val conn = virConnectOpen("qemu:///system")
    if (conn != null) {
        memScoped {
            val numDomains = virConnectNumOfDomains(conn) + virConnectNumOfDefinedDomains(conn)
            var domains = allocArray<CPointerVar<virDomainPtrVar>>(numDomains)
            val r = virConnectListAllDomains(conn, domains, VIR_CONNECT_LIST_DOMAINS_SHUTOFF)

            if (r < 0) {
                println("error")
            } else {
                for (i in 0..<r) {
                    domains = domains.reinterpret()
                    val x = domains[i]?.pointed?.value
                    val name = virDomainGetName(x)
                    println(name!!.toKString())
                    virDomainFree(x)
                }
            }
        }
    } else {
        println("Connection failed")
    }
}
