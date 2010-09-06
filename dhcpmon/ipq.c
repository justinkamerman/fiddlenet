/*
 * This code is GPL.
 */
#include <stdlib.h>
#include <stdio.h>
#include <sys/socket.h>
#include <linux/in.h>
#include <linux/in6.h>
#include <linux/ip.h>
#include <linux/udp.h>
#include <linux/netfilter.h>
#include <libipq.h>
#include "dhcp.h"

#define BUFSIZE 2048

#define NIPQUAD(addr) \
        ((unsigned char *)&addr)[0], \
        ((unsigned char *)&addr)[1], \
        ((unsigned char *)&addr)[2], \
        ((unsigned char *)&addr)[3]

#define NIPQUAD_FMT "%u.%u.%u.%u"


static void die(struct ipq_handle *h)
{
    ipq_perror("passer");
    ipq_destroy_handle(h);
    exit(1);
}

int main(int argc, char **argv)
{
    int status;
    unsigned char buf[BUFSIZE];
    struct ipq_handle *h;
    
    h = ipq_create_handle(0, NFPROTO_IPV4);
    if (!h)
        die(h);
    status = ipq_set_mode (h, IPQ_COPY_PACKET, BUFSIZE);
    if (status < 0)
        die(h);

    do
    {
        status = ipq_read(h, buf, BUFSIZE, 0);
        if (status < 0)
            die(h);

        switch (ipq_message_type(buf))
        {
        case NLMSG_ERROR:
            fprintf(stderr, "Received error message %d\n", ipq_get_msgerr(buf));
            break;
            
        case IPQM_PACKET: {
            ipq_packet_msg_t *m = ipq_get_packet (buf);

            printf ("Received packet: indev=%s outdev=%s, len=%d\n", m->indev_name, m->outdev_name, m->data_len);
            struct iphdr *iph;

            if (m->data_len > 0)
            {
                // Look at the ip header
                iph = (struct iphdr *) m->payload;

                printf ("\tIP:\tversion %d, length %d, ttl %d, protocol %d, srcip " NIPQUAD_FMT ", dstip " NIPQUAD_FMT "\n",
                        iph->version,
                        iph->ihl,
                        iph->ttl,
                        iph->protocol,
                        NIPQUAD (iph->saddr),
                        NIPQUAD (iph->daddr));
            }
            
            if (iph->protocol == 17)
            {
                // Look at the udp header
                struct udphdr *udph = (struct udphdr *) (m->payload + (iph->ihl * 4));
                printf ("\tUDP:\tsrcport %d, destport %d\n", ntohs (udph->source), ntohs (udph->dest));
                
                // Check payload size

                // Look for dhcp
                struct dhcphdr *dhcph = (struct dhcphdr *) (udph + 1);
                
                printf ("\tDHCP:\top %d, htype %d, hlen %d, hops %d\n", 
                        dhcph->op,
                        dhcph->htype,
                        dhcph->hlen,
                        dhcph->hops);

                printf ("\tDHCP:\txid 0x%x\n", ntohl (dhcph->xid));
                printf ("\tDHCP:\tsecs %d, flags 0x%x\n", ntohs (dhcph->secs), ntohs (dhcph->flags));
                printf ("\tDHCP:\tciaddr " NIPQUAD_FMT "\n", NIPQUAD (dhcph->ciaddr));
                printf ("\tDHCP:\tyiaddr " NIPQUAD_FMT "\n", NIPQUAD (dhcph->yiaddr));
                printf ("\tDHCP:\tsiaddr " NIPQUAD_FMT "\n", NIPQUAD (dhcph->siaddr));
                printf ("\tDHCP:\tgiaddr " NIPQUAD_FMT "\n", NIPQUAD (dhcph->giaddr));


                //printf ("DHCP>>> %02x %02x %02x %02x  %02x %02x %02x %02x\n", 
                //        p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7]);  
            }
            else
            {
                printf ("\tNot UDP\n");
            }

            printf ("________________________________________\n");

            status = ipq_set_verdict(h, m->packet_id, NF_ACCEPT, 0, NULL);
            if (status < 0) die (h);
            break;
        }

        default:
            fprintf(stderr, "Unknown message type!\n");
            break;
        }
    } while (1);

    ipq_destroy_handle(h);
    return 0;
}

