import { useEffect, useState } from 'react'

export const useInfiniteScroll = callback => {
  const [isFetching, setIsFetching] = useState(false)

  useEffect(() => {
    window.addEventListener('scroll', handleScroll, true)
    return () => window.removeEventListener('scroll', handleScroll)
  }, [])

  useEffect(() => {
    if (!isFetching) return
    callback()
  }, [isFetching])

  const handleScroll = () => {
    const mainContainer = document.getElementById('main-container')
    if (window.innerHeight + mainContainer.scrollTop !== mainContainer.scrollHeight || isFetching) {
      return
    }
    setIsFetching(true)
  }

  return [isFetching, setIsFetching]
}