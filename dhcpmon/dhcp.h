#ifndef _DHCP_H
#define _DHCP_H 1

#include <stdint.h>


struct dhcphdr
{
    uint8_t op;
    uint8_t htype;
    uint8_t hlen;
    uint8_t hops;
    uint32_t xid;
    uint16_t secs;
    uint16_t flags;
    uint32_t ciaddr;
    uint32_t yiaddr;
    uint32_t siaddr;
    uint32_t giaddr;
    uint8_t chaddr[20];
    char sname[64];
    char file[128];
    
};

#endif /* _DHCP_H */
