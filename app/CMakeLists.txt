cmake_minimum_required(VERSION 3.6)

project(MaterialFiles C)

# Keep linker build IDs stable across source checkout locations.
add_compile_options("-fdebug-prefix-map=${CMAKE_SOURCE_DIR}=.")

add_library(hiddenapi SHARED
        src/main/jni/hiddenapi.c)
target_compile_options(hiddenapi
        PRIVATE
        -Wall
        -Werror)

find_library(LOG_LIBRARY log)
add_library(syscall SHARED
        src/main/jni/syscall.c)
target_compile_options(syscall
        PRIVATE
        -Wall
        -Werror)
target_link_libraries(syscall
        PRIVATE
        ${LOG_LIBRARY})
