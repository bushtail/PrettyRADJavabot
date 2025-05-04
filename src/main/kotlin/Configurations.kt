enum class Configurations(val cfg: String, val arch: String, val ext: String) {
    DEFAULT("os=windows", "arch=x64", ".msi"),
    WIN_ARM("os=windows", "arch=aarch64", ".msi"),
    M1("os=mac", "arch=aarch64", ".pkg"),
    LINUX_AMD64("os=linux", "arch=x64", ".tar.gz")
}