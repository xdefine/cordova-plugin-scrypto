
cmake_minimum_required(VERSION 3.4.1)

add_library ( encrypt_token SHARED encrypt_token.cpp )
add_library ( md5 SHARED md5.cpp )
add_library ( sha1 SHARED sha1.cpp )
find_library( log-lib log )

target_link_libraries( encrypt_token md5 sha1 ${log-lib} )
